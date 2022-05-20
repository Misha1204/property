import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
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
  showLanguageOptions: boolean = false;

  // Header images
  headerImages!: { imageSrc: string }[];
  activeHeaderImageIndex: number = 0;

  // Sections
  sections: Section[] = [
    {
      id: 1,
      name: 'Area',
      address: 'თბილისი',
      description: 'lorem ipsum',
      image: null,
    },
  ];

  constructor(
    private landingPageService: LandingPageService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.headerImages = this.landingPageService.headerImages;

    this.landingPageService
      .getPropertyInfo()
      .pipe(
        tap(res => {
          this.sections = res;
        })
      )
      .subscribe();

    this.initForm();
  }

  initForm() {
    this.form = this.fb.group({
      name: this.fb.control(''),
      email: this.fb.control(''),
      phone: this.fb.control(''),
      address: this.fb.control(''),
    });
  }

  // Change Language of the page
  changeCurrentLanguage(language: string) {
    this.currentLanguage = language;
    this.showLanguageOptions = false;
  }

  // Slide through images in header
  slideHeaderImages(direction: string) {
    if (direction === 'forward') {
      if (this.activeHeaderImageIndex < this.headerImages.length - 1) {
        this.activeHeaderImageIndex++;
      } else {
        this.activeHeaderImageIndex = 0;
      }
    } else {
      if (this.activeHeaderImageIndex > 0) {
        this.activeHeaderImageIndex--;
      } else {
        this.activeHeaderImageIndex = this.headerImages.length - 1;
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

    this.landingPageService.addUserInfo(request).subscribe();
  }
}
