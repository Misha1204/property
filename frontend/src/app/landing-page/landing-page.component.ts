import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { tap } from 'rxjs';
import { Header } from '../models/header.model';
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

  // Sections
  sections!: Section[];

  constructor(
    private landingPageService: LandingPageService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.landingPageService
      .getHeaderInfo()
      .pipe(
        tap((res: any) => {
          this.headerInfo = res;
        })
      )
      .subscribe();

    this.landingPageService
      .getPropertyInfo()
      .pipe(
        tap(res => {
          console.log(res);

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

  slideSectionImages(direction: string, section: any) {
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
      },
      error: err => {
        console.log(err);
      },
    });
  }
}
