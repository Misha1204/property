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
  currentLanguage: string = 'Eng';

  // Header images
  headerInfo!: {
    description: string;
    descriptionEng: string;
    files: string[];
    id: number;
    images: string[];
  };

  companyLogos!: any;
  activeHeaderImageIndex: number = 0;
  activeSectionImageIndex: number = 0;

  extended = false;

  // Sections
  sections!: Section[];

  constructor(
    private landingPageService: LandingPageService,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.landingPageService
      .getHeaderInfo()
      .pipe(
        tap((res: any) => {
          console.log(res);

          this.headerInfo = res;
        })
      )
      .subscribe();

    this.landingPageService
      .getPropertyInfo()
      .pipe(
        tap(res => {
          this.sections = res;
        })
      )
      .subscribe();

    this.landingPageService
      .getCompanyLogos()
      .pipe(
        tap(res => {
          this.companyLogos = res;
        })
      )
      .subscribe();

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

  // Change Language of the page
  changeCurrentLanguage(event: Event) {
    this.currentLanguage = (event.target as HTMLInputElement).value;
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

  extendCollapseSectionInfo() {
    this.extended = !this.extended;
  }

  // Handle form submition
  onSubmit(sectionId: number) {
    const request: UserInfo = {
      name: this.form.get('name')?.value,
      email: this.form.get('email')?.value,
      phone: this.form.get('phone')?.value,
      address: this.form.get('address')?.value,
      sectionId,
    };

    this.landingPageService.addUserInfo(request).subscribe({
      next: () => {
        this.form.reset();
        this.toastr.success('მონაცემები წარმატებით დაემატა');
      },
      error: err => {
        this.toastr.error('მონაცემების დამატება ვერ მოხერხდა');
      },
    });
  }
}
