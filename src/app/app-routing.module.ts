// Angular Import
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// project import
import { AdminComponent } from './theme/layout/admin/admin.component';
import { GuestComponent } from './theme/layout/guest/guest.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: AdminComponent,
    children: [
      // {
      //   path: 'check',
      //   redirectTo: '/analytics',
      //   pathMatch: 'full'
      // },
      {
        path: 'charts',
        loadComponent: () => import('./demo/dashboard/dash-analytics/dash-analytics.component')
      },
      {
        path: 'component',
        loadChildren: () => import('./demo/ui-element/ui-basic.module').then((m) => m.UiBasicModule)
      },
      {
        path: 'chart',
        loadComponent: () => import('./demo/chart & map/core-apex/core-apex.component')
      },
      {
        path: 'forms',
        loadComponent: () => import('./demo/forms & tables/form-elements/form-elements.component')
      },
      {
        path: 'tables',
        loadComponent: () => import('./demo/forms & tables/tbl-bootstrap/tbl-bootstrap.component')
      },
      {
        path: 'sample-page',
        loadComponent: () => import('./demo/sample-page/sample-page.component')
      }
    ]
  },
  // {
  //   path: '',
  //   component: GuestComponent,
  //   children: [
  //     {
  //       path: 'auth/signup',
  //       loadComponent: () => import('./demo/authentication/sign-up/sign-up.component')
  //     },
  //     {
  //       path: 'auth/signin',
  //       loadComponent: () => import('./demo/authentication/sign-in/sign-in.component')
  //     }
  //   ]
  // },
  // {
  //   path: '',
  //   component: LoginComponent,
  // },
  {
    path: '',
    component: LoginComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
