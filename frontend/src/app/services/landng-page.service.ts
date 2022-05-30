import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Header } from '../models/header.model';
import { Section } from '../models/section.model';
import { UserInfo } from '../models/user-info.model';

@Injectable({
  providedIn: 'root',
})
export class LandingPageService {
  admin = {
    email: 'admin@admin.admin',
    password: '12345678',
  };

  constructor(private http: HttpClient) {}

  // Auth
  login(request: { email: string; password: string }) {
    if (
      this.admin.email === request.email &&
      this.admin.password === request.password
    ) {
      localStorage.setItem('IS_USER_LOGGED_IN', 'TRUE');
      return true;
    }

    return false;
  }

  logout() {
    localStorage.clear();
  }

  // Header Methods
  getHeaderInfo() {
    return this.http.get<Header>(`${environment.api_url}/api/header`);
  }

  updateHeaderInfo(formData: any) {
    return this.http.put(`${environment.api_url}/api/header`, formData);
  }

  // Section Methods
  getPropertyInfo() {
    return this.http.get<Section[]>(`${environment.api_url}/api/property`);
  }

  getPropertyInfoById(id: number) {
    return this.http.get<Section>(`${environment.api_url}/api/property/${id}`);
  }

  addSectionInfo(formData: any) {
    return this.http.post(`${environment.api_url}/api/property`, formData);
  }

  editSection(sectionId: number, formData: any) {
    return this.http.put(
      `${environment.api_url}/api/property/${sectionId}`,
      formData
    );
  }

  deleteSection(sectionId: number) {
    return this.http.delete(`${environment.api_url}/api/property/${sectionId}`);
  }

  uploadImages(fd: any) {
    return this.http.post(
      `${environment.api_url}/api/property/uploadImages/4`,
      fd
    );
  }

  addUserInfo(request: UserInfo) {
    return this.http.post(`${environment.api_url}/api/subscriber`, request);
  }

  addHeaderInfo(formData: any) {
    return this.http.post(`${environment.api_url}/api/header`, formData);
  }

  // Company logos
  getCompanyLogos() {
    return this.http.get<
      {
        id: 0;
        link: 'string';
        image: 'string';
      }[]
    >(`${environment.api_url}/api/slider`);
  }

  addCompanyLogo(formData: any) {
    return this.http.post(`${environment.api_url}/api/slider`, formData);
  }

  deleteCompanyLogo(logoId: number) {
    return this.http.delete(`${environment.api_url}/api/slider/${logoId}`);
  }
}
