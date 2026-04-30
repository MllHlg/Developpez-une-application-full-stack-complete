import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ThemeService } from './theme';
import { Theme } from '../models/theme';

describe('ThemeService', () => {
  let service: ThemeService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ThemeService]
    });
    service = TestBed.inject(ThemeService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all themes', () => {
    const mockThemes: Theme[] = [
      { id: 1, titre: 'Thème 1', description: 'Description du thème 1', abonnement: true },
      { id: 2, titre: 'Thème 2', description: 'Description du thème 2', abonnement: false }
    ];

    service.all().subscribe(themes => {
      expect(themes).toEqual(mockThemes);
      expect(themes.length).toBe(2);
    });

    const req = httpTestingController.expectOne('themes');
    expect(req.request.method).toBe('GET');
    req.flush(mockThemes);
  });

  it('should subscribe to a theme', () => {
    const themeId = 1;
    const mockResponse = { message: 'Abonnement réussi' };

    service.abonnement(themeId).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpTestingController.expectOne(`themes/${themeId}/abonnement`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toBeNull(); 
    req.flush(mockResponse);
  });

  it('should unsubscribe from a theme', () => {
    const themeId = 1;
    const mockResponse = { message: 'Désabonnement réussi' };

    service.desabonnement(themeId).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpTestingController.expectOne(`themes/${themeId}/abonnement`);
    expect(req.request.method).toBe('DELETE');

    req.flush(mockResponse);
  });
});