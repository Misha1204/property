import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminComponent } from './admin.component';
import { SectionsComponent } from './sections/sections.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { AdminsRoutingModule } from './admin-routing.module';
import { AuthComponent } from './auth/auth.component';
import { AddHeaderComponent } from './add-edit-header/add-header.component';
import { AddSectionComponent } from './add-edit-section/add-section.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AdminComponent,
    SectionsComponent,
    AuthComponent,
    AddHeaderComponent,
    AddSectionComponent,
  ],
  imports: [
    CommonModule,
    MatPaginatorModule,

    MatTableModule,
    MatInputModule,
    MatButtonModule,
    AdminsRoutingModule,
    ReactiveFormsModule,
  ],
})
export class AdminModule {}
