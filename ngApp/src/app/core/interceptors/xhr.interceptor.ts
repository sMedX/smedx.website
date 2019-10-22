import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from "@angular/common/http";

import { Observable } from "rxjs";
import { switchMap, tap, map } from 'rxjs/operators';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // const xhr = req.clone({
        //     headers: req.headers.set('X-Request-With', 'XMLHttpRequest')
        // });
        // return next.handle(xhr);
        if (req.responseType === 'json') {

            req = req.clone({ responseType: 'text' });

            return next.handle(req).pipe(
                map(response => {
                    if (response instanceof HttpResponse) {
                        // console.log(response.body);
                        // console.log('****************************');
                        // console.log(response.body.substr(12480, 350));
                        // console.log(response.body.indexOf('Error'));
                        if (response.body.indexOf('Error') > -1) {
                            console.log(response.body.substr(response.body.indexOf('Error'), response.body.length));
                            response = response.clone({ body: response.body.substr(0, response.body.indexOf('Error')) });
                        }

                        response = response.clone(
                            {
                                body: JSON.parse(response.body),
                                headers: req.headers.set('X-Request-With', 'XMLHttpRequest')
                            }
                        );
                    }

                    return response;
                })
            );
        } else {
            req = req.clone({ headers: req.headers.set('X-Request-With', 'XMLHttpRequest') });
        }
    }

}
