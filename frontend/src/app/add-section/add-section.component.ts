import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LandingPageService } from '../services/landng-page.service';

@Component({
  selector: 'app-add-section',
  templateUrl: './add-section.component.html',
  styleUrls: ['./add-section.component.scss'],
})
export class AddSectionComponent implements OnInit {
  form!: FormGroup;
  formData = new FormData();

  constructor(
    private fb: FormBuilder,
    private landingPageService: LandingPageService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.form = this.fb.group({
      name: this.fb.control(''),
      title: this.fb.control(''),
      city: this.fb.control(''),
      country: this.fb.control(''),
      description: this.fb.control(''),
      nameEng: this.fb.control(''),
      titleEng: this.fb.control(''),
      cityEng: this.fb.control(''),
      countryEng: this.fb.control(''),
      descriptionEng: this.fb.control(''),
    });
  }

  onImageSelected(event: any) {
    let counter = 1;
    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.formData.append(
          `image${counter}`,
          event.target.files[+property],
          event.target.files[+property].name
        );
        counter++;
      }
    });
  }

  onPdfSelected(event: any) {
    this.formData.append(
      'file',
      event.target.files[0],
      event.target.files[0].name
    );
  }

  onCreateSection() {
    // Object.keys(this.form.value).forEach(key =>
    //   this.formData.append(key, this.form.value[key])
    // );

    const dataJson = JSON.stringify(this.form.value);
    this.formData.append('sectionData', dataJson);

    this.landingPageService.addSectionInfo(this.formData).subscribe();
  }
}
