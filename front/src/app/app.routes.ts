import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home';
import { ThemesList } from './features/themes/themes';
import { ArticlesList } from './features/articles/articles-list/articles-list';
import { Login } from './features/login/login';
import { Register } from './features/register/register';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'themes', component: ThemesList},
    {path: 'articles', component: ArticlesList},
    {path: 'login', component: Login},
    {path: 'register', component: Register}
];
