import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';
import { SectionPostRequest } from '../models/add-section.model';
import { Header } from '../models/header.model';
import { Section } from '../models/section.model';
import { UserInfo } from '../models/user-info.model';

@Injectable({
  providedIn: 'root',
})
export class LandingPageService {
  constructor(private http: HttpClient) {}

  companyLogos = [
    {
      imageSrc: 'assets/company-logos/gcf.png',
    },
    {
      imageSrc: 'assets/company-logos/Fiabci.png',
    },
    {
      imageSrc: 'assets/company-logos/Bank_of_georgia.png',
    },
    {
      imageSrc: 'assets/company-logos/gcf.png',
    },
    {
      imageSrc: 'assets/company-logos/Fiabci.png',
    },
    {
      imageSrc: 'assets/company-logos/Bank_of_georgia.png',
    },
    {
      imageSrc: 'assets/company-logos/gcf.png',
    },
    {
      imageSrc: 'assets/company-logos/Fiabci.png',
    },
    {
      imageSrc: 'assets/company-logos/Bank_of_georgia.png',
    },
    {
      imageSrc: 'assets/company-logos/gcf.png',
    },
    {
      imageSrc: 'assets/company-logos/Fiabci.png',
    },
    {
      imageSrc: 'assets/company-logos/Bank_of_georgia.png',
    },
  ];

  // Auth
  login(request: any) {
    return this.http.post(`http://localhost:8080/api/user/login`, request);
  }

  logout() {
    return this.http.post(`http://localhost:8080/api/user/logout`, {}).pipe(
      tap(res => {
        localStorage.clear();
      })
    );
  }

  // Header Methods
  getHeaderInfo() {
    return this.http.get<Header>(`http://localhost:8080/api/header`);
  }

  updateHeaderInfo(formData: any) {
    return this.http.put(`http://localhost:8080/api/header`, formData);
  }

  // Section Methods
  getPropertyInfo() {
    return this.http.get<Section[]>(`http://localhost:8080/api/property`);
  }

  getPropertyInfoById(id: number) {
    return this.http.get<Section>(`http://localhost:8080/api/property/${id}`);
  }

  addSectionInfo(formData: any) {
    return this.http.post(`http://localhost:8080/api/property`, formData);
  }

  editSection(sectionId: number, formData: any) {
    return this.http.put(
      `http://localhost:8080/api/property/${sectionId}`,
      formData
    );
  }

  uploadImages(fd: any) {
    return this.http.post(
      `http://localhost:8080/api/property/uploadImages/4`,
      fd
    );
  }

  addUserInfo(request: UserInfo) {
    return this.http.post(`http://localhost:8080/api/subscriber`, request);
  }

  addHeaderInfo(formData: any) {
    return this.http.post(`http://localhost:8080/api/header`, formData);
  }

  deleteSection(sectionId: number) {
    return this.http.delete(`http://localhost:8080/api/property/${sectionId}`);
  }
}
