import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home';
import { ThemesList } from './features/themes/themes_list/themes-list';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'themes', component: ThemesList}
];
