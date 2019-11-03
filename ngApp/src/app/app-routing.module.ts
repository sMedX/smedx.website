import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './pages/about/about.component';
import { HomeComponent } from './home/home.component';
import { CompetenciesComponent } from './pages/competencies/competencies.component';
import { ContactsComponent } from './pages/contacts/contacts.component';
import { CasesPageComponent } from './pages/cases-page/cases-page.component';
import { ProductComponent } from './pages/product/product.component';
import { NewsComponent } from './pages/news/news.component';
import { AgreementComponent } from './pages/agreement/agreement.component';
import { PartnersDetailedComponent } from './pages/partners-detailed/partners-detailed.component';
import { NewsdetailsComponent } from './pages/news/newsdetails/newsdetails.component';
import { ArticleComponent } from './pages/competencies/article/article.component';
import { ArticleGuard } from './guards/article.guard';
import { ProposalComponent } from './home/proposal/proposal.component';
import { PresentationComponent } from './home/presentation/presentation.component';
import { ApplicablespheresComponent } from './home/applicablespheres/applicablespheres.component';
import { SubscribeFormComponent } from './home/subscribe-form/subscribe-form.component';
import { PartnersComponent } from './home/partners/partners.component';
import { NewsEditorComponent } from './pages/news/news-editor/news-editor.component';

const routes: Routes = [
  { path: '', redirectTo: '/main', pathMatch: 'full' },
  { path: 'newsblog', component: NewsComponent },
  { path: 'newsblog/:newslink', component: NewsdetailsComponent },
  { path: 'newseditor/:newsId/:hash', component: NewsEditorComponent },
  { path: 'cases', component: CasesPageComponent },
  { path: 'product', component: ProductComponent },
  { path: 'about', component: AboutComponent },
  { path: 'main', component: HomeComponent },
  { path: 'competencies', component: CompetenciesComponent },
  { path: 'competencies/:articlelink', component: ArticleComponent, canActivate: [ArticleGuard] },
  { path: 'contacts', component: ContactsComponent },
  { path: 'agreement', component: AgreementComponent },
  { path: 'partners', component: PartnersDetailedComponent }
  // test:
  ,
  { path: 'presentation', component: PresentationComponent },
  { path: 'proposal', component: ProposalComponent },
  { path: 'applicable', component: ApplicablespheresComponent },
  { path: 'subscribe', component: SubscribeFormComponent },
  { path: 'partners_part', component: PartnersComponent },

  // {path: '**', redirectTo: '/main'}, // redirect all unknown routes to main
  // {path: '', redirectTo: '/main'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes,
    { enableTracing: false }// only for test
  )],
  exports: [RouterModule]
})
export class AppRoutingModule { }


