import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { tap } from 'rxjs';
import { Section } from '../models/section.model';
import { UserInfo } from '../models/user-info.model';
import { LandingPageService } from '../services/landng-page.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss'],
})
export class LandingPageComponent implements OnInit {
  form!: FormGroup;

  // Language variables
  currentLanguage: string = 'eng';

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
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.getHeaderInfo();
    this.getPropertyInfo();
    this.getSliderInfo();

    this.initForm();
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
        tap(res => {
          console.log(res);
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

    this.getHeaderInfo();
    this.getPropertyInfo();
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

  extendCollapseSectionInfo(mobileSectionInfoRef: HTMLElement) {
    this.isMobileInfoExtended = !this.isMobileInfoExtended;
    if (this.isMobileInfoExtended) {
      (mobileSectionInfoRef.children[0] as HTMLElement).style.height = '150px';
    } else {
      (mobileSectionInfoRef.children[0] as HTMLElement).style.height = '72px';
    }
  }

  extendCollapseForm(mobileFormRef: HTMLElement) {
    this.isMobileFormExtended = !this.isMobileFormExtended;
    console.log(this.isMobileFormExtended);

    if (this.isMobileFormExtended) {
      mobileFormRef.style.height = '350px';
      console.log(mobileFormRef.style.height);
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
  }
}
