import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { LandingPageService } from 'src/app/services/landng-page.service';

@Component({
  selector: 'app-add-header',
  templateUrl: './add-header.component.html',
  styleUrls: ['./add-header.component.scss'],
})
export class AddHeaderComponent implements OnInit {
  form!: FormGroup;
  formData = new FormData();

  constructor(
    private fb: FormBuilder,
    private landingPageService: LandingPageService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      description: new FormControl('', Validators.required),
      descriptionEng: new FormControl('', Validators.required),
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
    let counter = 1;
    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.formData.append(
          `file${counter}`,
          event.target.files[+property],
          event.target.files[+property].name
        );
        counter++;
      }
    });
  }

  onUpdateHeader() {
    Object.keys(this.form.value).forEach(key =>
      this.formData.append(key, this.form.value[key])
    );

    this.landingPageService.addHeaderInfo(this.formData).subscribe();
  }
}
