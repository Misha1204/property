import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { map, tap } from 'rxjs';
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

  deletedImageIds: number[] = [];
  deletedFileIds: number[] = [];

  constructor(
    private fb: FormBuilder,
    private landingPageService: LandingPageService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.getHeaderInfo();
  }

  getHeaderInfo() {
    this.landingPageService
      .getHeaderInfo()
      .pipe(
        tap(res => {
          if (!res) {
            this.initForm();
          }
        }),
        map(res => {
          if (res) {
            for (let i = 0; i < res.images.length; i++) {
              res.images[i] = { id: i + 1, image: res.images[i] };
            }

            for (let i = 0; i < res.files.length; i++) {
              res.files[i] = { id: i + 1, file: res.files[i] };
            }

            this.headerInfo = res;
            this.initForm();
          }
          return res;
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

    let counter = 0;
    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.formData.append(
          `image${
            this.headerInfo ? this.deletedImageIds[counter] : counter + 1
          }`,
          event.target.files[+property],
          event.target.files[+property].name
        );
        counter++;
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

    let counter = 0;
    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.formData.append(
          `file${this.headerInfo ? this.deletedFileIds[counter] : counter + 1}`,
          event.target.files[+property],
          event.target.files[+property].name
        );
        counter++;
      }
    });
  }

  deleteImage(imageId: number) {
    this.headerInfo.images = this.headerInfo.images.filter(
      res => res.id !== imageId
    );
    this.deletedImageIds.push(imageId);
  }

  deleteFile(fileId: number) {
    this.headerInfo.files = this.headerInfo.files.filter(
      res => res.id !== fileId
    );
    this.deletedFileIds.push(fileId);
  }

  onUpdateHeader() {
    if (this.headerInfo && this.headerInfo.images.length > 0) {
      this.headerInfo.images.forEach(image => {
        this.formData.append(`imageAddress${image.id}`, image.image);
      });
    }

    if (this.headerInfo && this.headerInfo.files.length > 0) {
      this.headerInfo.files.forEach(file => {
        this.formData.append(`fileAddress${file.id}`, file.file);
      });
    }

    Object.keys(this.form.value).forEach(key => {
      if (key !== 'images' && key !== 'files') {
        this.formData.append(key, this.form.value[key]);
      }
    });

    if (this.headerInfo) {
      this.landingPageService.updateHeaderInfo(this.formData).subscribe({
        next: () => {
          this.resetData();
        },
        error: () => {
          this.resetData();
        },
      });
    } else {
      this.landingPageService.addHeaderInfo(this.formData).subscribe({
        next: () => {
          this.resetData();
        },
        error: () => {
          this.resetData();
        },
      });
    }
  }

  resetData() {
    this.form.reset();
    this.formData = new FormData();
    this.deletedImageIds = [];
    this.deletedFileIds = [];
    this.getHeaderInfo();
  }

  downloadExcel() {
    this.http
      .get(`/api/subscriber/export`, { responseType: 'arraybuffer' })
      .subscribe(res => {
        console.log(res);
      });
  }
}
