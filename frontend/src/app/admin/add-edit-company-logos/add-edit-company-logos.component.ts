import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { LandingPageService } from 'src/app/services/landng-page.service';

@Component({
  selector: 'app-add-edit-company-logos',
  templateUrl: './add-edit-company-logos.component.html',
  styleUrls: ['./add-edit-company-logos.component.scss'],
})
export class AddEditCompanyLogosComponent implements OnInit {
  form!: FormGroup;
  formData!: FormData;

  constructor(
    private landingPageService: LandingPageService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.form = new FormGroup({
      link: new FormControl(''),
      image: new FormControl(''),
    });
  }

  onImageSelected(event: any) {
    this.formData = new FormData();
    this.formData.append(
      'image',
      event.target.files[0],
      event.target.files[0].name
    );
  }

  onUpload() {
    this.formData.append('link', this.form.get('link')?.value);
    this.landingPageService.addCompanyLogo(this.formData).subscribe(() => {
      this.formData = new FormData();
      this.location.back();
    });
  }
}
