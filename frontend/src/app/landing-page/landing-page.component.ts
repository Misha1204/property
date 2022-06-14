import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { map, tap } from 'rxjs';
import { Section } from '../models/section.model';
import { UserInfo } from '../models/user-info.model';
import { LandingPageService } from '../services/landng-page.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss'],
})
export class LandingPageComponent implements OnInit, AfterViewInit {
  form!: FormGroup;

  @ViewChild('mobileSlider') mobileSlider!: any;

  // Language variables
  currentLanguage!: string;

  // Header images
  headerInfo!: {
    description: string;
    files: string[];
    images: string[];
  };

  companyLogos!: any;
  activeHeaderImageIndex: number = 0;
  activeSectionImageIndex: number = 0;

  isMobileInfoExtended = false;
  isMobileFormExtended = false;

  // Sections
  sections!: Section[];

  constructor(
    private landingPageService: LandingPageService,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.currentLanguage = router.url.slice(1, 4);
  }

  ngOnInit(): void {
    this.getHeaderInfo();
    this.getPropertyInfo();
    this.getSliderInfo();

    this.initForm();
  }

  ngAfterViewInit() {
    const element = this.mobileSlider.nativeElement as HTMLElement;

    setInterval(() => {
      element.scrollLeft += element.clientWidth;
      if (element.scrollLeft >= element.scrollWidth - element.clientWidth) {
        element.scrollLeft = 0;
      }
    }, 5000);
  }

  initForm() {
    this.form = this.fb.group({
      name: this.fb.control('', Validators.required),
      email: this.fb.control('', Validators.required),
      phone: this.fb.control('', Validators.required),
      address: this.fb.control('', Validators.required),
    });
  }

  getHeaderInfo() {
    this.landingPageService
      .getHeaderInfo(this.currentLanguage)
      .pipe(
        tap((res: any) => {
          this.headerInfo = res;
        })
      )
      .subscribe();
  }

  getPropertyInfo() {
    this.landingPageService
      .getPropertyInfo(this.currentLanguage)
      .pipe(
        map(res => {
          res.forEach(section => {
            section.isMobileDescriptionExtended = false;
            section.isMobileFormExtended = false;
          });

          return res;
        }),
        tap(res => {
          this.sections = res;
        })
      )
      .subscribe();
  }

  getSliderInfo() {
    this.landingPageService
      .getCompanyLogos()
      .pipe(
        tap(res => {
          this.companyLogos = res;
        })
      )
      .subscribe();
  }

  // Change Language of the page
  changeCurrentLanguage(event: Event) {
    this.currentLanguage = (event.target as HTMLInputElement).value;
    this.router.navigate([this.currentLanguage]);
  }

  // Slide through images in header
  slideHeaderImages(direction: string) {
    if (direction === 'forward') {
      if (this.activeHeaderImageIndex < this.headerInfo.images.length - 1) {
        this.activeHeaderImageIndex++;
      } else {
        this.activeHeaderImageIndex = 0;
      }
    } else {
      if (this.activeHeaderImageIndex > 0) {
        this.activeHeaderImageIndex--;
      } else {
        this.activeHeaderImageIndex = this.headerInfo.images.length - 1;
      }
    }
  }

  slideSectionImages(
    sectionRef: HTMLElement,
    direction?: string | undefined,
    section?: any
  ) {
    if (direction === 'forward') {
      if (this.activeSectionImageIndex < section.images.length - 1) {
        this.activeSectionImageIndex++;
      } else {
        this.activeSectionImageIndex = 0;
      }
    } else {
      if (this.activeSectionImageIndex > 0) {
        this.activeSectionImageIndex--;
      } else {
        this.activeSectionImageIndex = section.images.length - 1;
      }
    }

    sectionRef.style.backgroundImage = `url(${
      section.images[this.activeSectionImageIndex]
    })`;

    (
      sectionRef.children[2].children[1].children[
        this.activeSectionImageIndex
      ] as HTMLElement
    ).classList.add('opacity-1');
  }

  extendCollapseSectionInfo(mobileSectionInfoRef: HTMLElement, index: number) {
    this.sections[index].isMobileDescriptionExtended =
      !this.sections[index].isMobileDescriptionExtended;
    if (this.sections[index].isMobileDescriptionExtended) {
      (mobileSectionInfoRef.children[0] as HTMLElement).style.height =
        'fit-content';
    } else {
      (mobileSectionInfoRef.children[0] as HTMLElement).style.height = '72px';
    }
  }

  extendCollapseForm(mobileFormRef: HTMLElement, index: number) {
    this.sections[index].isMobileFormExtended =
      !this.sections[index].isMobileFormExtended;
    if (this.sections[index].isMobileFormExtended) {
      mobileFormRef.style.height = '350px';
    } else {
      mobileFormRef.style.height = '60px';
    }
  }

  // Handle form submition
  onSubmit(section: Section, mobileFormRef?: HTMLElement) {
    const request: UserInfo = {
      name: this.form.get('name')?.value,
      email: this.form.get('email')?.value,
      phone: this.form.get('phone')?.value,
      address: this.form.get('address')?.value,
      propertyId: section.id,
      propertyName: section.name,
      propertyTitle: section.title,
    };

    if (this.form.valid) {
      this.landingPageService.addUserInfo(request).subscribe({
        next: () => {
          this.form.reset();
          this.toastr.success('მონაცემები წარმატებით დაემატა');
          if (mobileFormRef) {
            mobileFormRef.style.height = '60px';
            this.isMobileFormExtended = false;
          }
        },
        error: err => {
          this.toastr.error('მონაცემების დამატება ვერ მოხერხდა');
        },
      });
    } else {
      this.currentLanguage === 'eng'
        ? this.toastr.error('All Fields are Required')
        : this.toastr.error('გთხოვთ, შეავსოთ ყველა ველი');
    }
  }
}
