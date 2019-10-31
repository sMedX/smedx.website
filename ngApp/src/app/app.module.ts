import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Optional, SkipSelf, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { PresentationComponent } from './home/presentation/presentation.component';
import { ProposalComponent } from './home/proposal/proposal.component';
import { ApplicablespheresComponent } from './home/applicablespheres/applicablespheres.component';
import { ContactFormComponent } from './home/contact-form/contact-form.component';
import { SubscribeFormComponent } from './home/subscribe-form/subscribe-form.component';
import { CasesComponent } from './home/cases/cases.component';
import { PartnersComponent } from './home/partners/partners.component';
import { AboutComponent } from './pages/about/about.component';
import { AppRoutingModule } from './app-routing.module';
import { CompetenciesComponent } from './pages/competencies/competencies.component';
import { ContactsComponent } from './pages/contacts/contacts.component';
import { ProductComponent } from './pages/product/product.component';
import { CasesPageComponent } from './pages/cases-page/cases-page.component';
import { NewsComponent } from './pages/news/news.component';
import { AgreementComponent } from './pages/agreement/agreement.component';
import { PartnersDetailedComponent } from './pages/partners-detailed/partners-detailed.component';
// import { GraphQLModule } from './graphql.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { GraphQlModule } from './core/graph-ql/graph-ql.module';
import { HttpLinkModule } from 'apollo-angular-link-http';
import { XhrInterceptor } from './core/interceptors/xhr.interceptor';
import { NewsdetailsComponent } from './pages/news/newsdetails/newsdetails.component';
import { ArticleComponent } from './pages/competencies/article/article.component';
import { NewsEditorComponent } from './pages/news/news-editor/news-editor.component';
// import { NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    PresentationComponent,
    ProposalComponent,
    ApplicablespheresComponent,
    ContactFormComponent,
    SubscribeFormComponent,
    CasesComponent,
    PartnersComponent,
    AboutComponent,
    CompetenciesComponent,
    ContactsComponent,
    ProductComponent,
    CasesPageComponent,
    NewsComponent,
    AgreementComponent,
    PartnersDetailedComponent,
    NewsdetailsComponent,
    ArticleComponent,
    NewsEditorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GraphQlModule,
    HttpClientModule,
    HttpLinkModule,
    FormsModule,
    // NgbModule.forRoot(),
    // NgForm,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(@Optional() @SkipSelf() @Inject(AppModule) parentModule: AppModule | null) {
    if (parentModule) {
      throw new Error(
        'AppModule has already been loaded.'
      );
    }
  }
}
