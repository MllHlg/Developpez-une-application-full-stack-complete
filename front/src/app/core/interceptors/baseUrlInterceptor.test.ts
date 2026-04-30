import { TestBed } from '@angular/core/testing';
import { HttpRequest } from '@angular/common/http';
import { baseUrlInterceptor } from './baseUrlInterceptor';
import { environment } from '../../../environments/environment';

describe('BaseUrlInterceptor', () => {
  it('should add the base URL to the request', (done) => {
    const request = new HttpRequest('GET', 'api/users');
    const nextFn = (req: HttpRequest<any>) => {
      expect(req.url).toBe(`${environment.baseUrl}/api/users`);
      done();
      return {} as any;
    };

    TestBed.runInInjectionContext(() => {
      baseUrlInterceptor(request, nextFn);
    });
  });
});