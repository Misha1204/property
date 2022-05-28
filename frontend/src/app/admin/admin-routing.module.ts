import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../guards/auth.guard';
import { AddHeaderComponent } from './add-edit-header/add-header.component';
import { AddSectionComponent } from './add-edit-section/add-section.component';
import { AdminComponent } from './admin.component';
import { SectionsComponent } from './sections/sections.component';
import { LogosSectionComponent } from './logos-section/logos-section.component';
import { AddEditCompanyLogosComponent } from './add-edit-company-logos/add-edit-company-logos.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'add_header',
        component: AddHeaderComponent,
      },
      {
        path: 'sections',
        component: SectionsComponent,
      },
      {
        path: 'sections/add_section',
        component: AddSectionComponent,
      },
      {
        path: 'sections/edit_section/:id',
        component: AddSectionComponent,
      },
      {
        path: 'company_logos',
        component: LogosSectionComponent,
      },
      {
        path: 'company_logos/add_logo',
        component: AddEditCompanyLogosComponent,
      },
      {
        path: 'company_logos/edit_logos/:id',
        component: AddEditCompanyLogosComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminsRoutingModule {}
