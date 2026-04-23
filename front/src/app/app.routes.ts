import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home';
import { ThemesList } from './features/themes/themes';
import { ArticlesList } from './features/articles/articles-list/articles-list';
import { Login } from './features/login/login';
import { Register } from './features/register/register';
import { ArticleCreation } from './features/articles/article-creation/article-creation';
import { ArticleDetails } from './features/articles/article-details/article-details';
import { Me } from './features/me/me';
import { unauthGuard } from './core/guards/unauth';
import { authGuard } from './core/guards/auth';
import { NotFound } from './features/not-found/not-found';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
    },
    {
        path: 'login',
        canActivate: [unauthGuard],
        component: Login,

    },
    {
        path: 'register',
        canActivate: [unauthGuard],
        component: Register,

    },
    {
        path: 'articles',
        canActivate: [authGuard],
        children: [
            {
                path: '',
                component: ArticlesList,
                data: { title: 'Articles' },
            },
            {
                path: 'create',
                component: ArticleCreation,
                data: { title: 'Articles - create' },
            },
            {
                path: ':id',
                component: ArticleDetails,
                data: { title: 'Articles - detail' },
            },
        ],
    },
    {
        path: 'themes',
        canActivate: [authGuard],
        component: ThemesList,
    },
    {
        path: 'user',
        canActivate: [authGuard],
        component: Me,
    },
    { path: '404', component: NotFound },
    { path: '**', redirectTo: '404' },
];
