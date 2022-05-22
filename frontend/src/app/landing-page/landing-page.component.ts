import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  headerImages!: { imageSrc: string }[];
  companyLogos!: any;
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
    {
      id: 2,
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
    this.companyLogos = this.landingPageService.companyLogos;

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
