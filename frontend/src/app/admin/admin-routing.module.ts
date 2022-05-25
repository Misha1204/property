import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddHeaderComponent } from './add-edit-header/add-header.component';
import { AddSectionComponent } from './add-edit-section/add-section.component';
import { AdminComponent } from './admin.component';
import { SectionsComponent } from './sections/sections.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
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
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminsRoutingModule {}
