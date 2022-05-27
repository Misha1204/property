import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth/auth.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthRoutingModule } from './auth-routing.module';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [AuthComponent],
  imports: [
    CommonModule,
    MatPaginatorModule,

    MatTableModule,
    MatInputModule,
    MatButtonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
  ],
})
export class AuthModule {}
