import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { tap } from 'rxjs';
import { Header } from 'src/app/models/header.model';
import { LandingPageService } from 'src/app/services/landng-page.service';

@Component({
  selector: 'app-add-header',
  templateUrl: './add-header.component.html',
  styleUrls: ['./add-header.component.scss'],
})
export class AddHeaderComponent implements OnInit {
  form!: FormGroup;
  formData = new FormData();

  headerInfo!: Header;

  imagesCounter = 1;
  filesCounter = 1;
  imagesAddressCounter = 1;
  filesAddressCounter = 1;

  constructor(
    private fb: FormBuilder,
    private landingPageService: LandingPageService
  ) {}

  ngOnInit(): void {
    this.landingPageService
      .getHeaderInfo()
      .pipe(
        tap(res => {
          if (res) {
            this.headerInfo = res;
            this.initForm();
          } else {
            this.initForm();
          }
        })
      )
      .subscribe();
  }

  initForm() {
    this.form = this.fb.group({
      description: new FormControl(
        this.headerInfo ? this.headerInfo.description : '',
        Validators.required
      ),
      descriptionEng: new FormControl(
        this.headerInfo ? this.headerInfo.descriptionEng : '',
        Validators.required
      ),
      images: new FormControl(''),
      files: new FormControl(''),
    });
  }

  onImageSelected(event: any) {
    if (
      this.headerInfo &&
      event.target.files.length + this.headerInfo.images.length > 4
    ) {
      this.form.get('images')?.reset();
      this.form.get('images')?.setErrors({
        limitation: 'შესაძლებელია მაქსიმუმ ოთხი ფოტოს ატვირთვა',
      });
      return;
    }

    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.formData.append(
          `image${this.imagesCounter}`,
          event.target.files[+property],
          event.target.files[+property].name
        );
        this.imagesCounter++;
      }
    });
  }

  onPdfSelected(event: any) {
    if (
      this.headerInfo &&
      event.target.files.length + this.headerInfo.files.length > 2
    ) {
      this.form.get('files')?.reset();
      this.form.get('files')?.setErrors({
        limitation: 'შესაძლებელია მაქსიმუმ ორი PDF ფაილის ატვირთვა',
      });
      return;
    }

    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.formData.append(
          `file${this.filesCounter}`,
          event.target.files[+property],
          event.target.files[+property].name
        );
        this.filesCounter++;
      }
    });
  }

  deleteImage(image: string) {
    this.headerInfo.images = this.headerInfo.images.filter(
      res => res !== image
    );
  }

  onUpdateHeader() {
    if (this.headerInfo && this.headerInfo.images.length > 0) {
      this.headerInfo.images.forEach(image => {
        this.formData.append(`imageAddress${this.imagesAddressCounter}`, image);
        this.imagesAddressCounter++;
      });
    }

    if (this.headerInfo && this.headerInfo.files.length > 0) {
      this.headerInfo.files.forEach(file => {
        this.formData.append(`fileAddress${this.filesAddressCounter}`, file);
        this.filesAddressCounter++;
      });
    }

    Object.keys(this.form.value).forEach(key => {
      if (key !== 'images' && key !== 'files') {
        this.formData.append(key, this.form.value[key]);
      }
    });

    if (this.headerInfo) {
      this.landingPageService.updateHeaderInfo(this.formData).subscribe();
    } else {
      this.landingPageService.addHeaderInfo(this.formData).subscribe();
    }
  }
}
