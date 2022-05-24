import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-section',
  templateUrl: './add-section.component.html',
  styleUrls: ['./add-section.component.scss'],
})
export class AddSectionComponent implements OnInit {
  form!: FormGroup;

  selectedImages: any = [];

  constructor(private fb: FormBuilder) {}

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
      images: this.fb.array([]),
      file: this.fb.control(null),
    });
  }

  onImageSelected(event: any) {
    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.form.get('images')?.value.push(event.target.files[+property]);
      }
    });
  }

  onPdfSelected(event: any) {
    this.form.get('file')?.setValue(event.target.files[0]);
    console.log(this.form.value);
  }

  onCreateSection() {
    console.log(this.form.value);
  }
}
