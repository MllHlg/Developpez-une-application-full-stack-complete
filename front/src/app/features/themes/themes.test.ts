import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { ThemesList } from './themes';
import { ThemeService } from '../../core/services/theme';
import { Theme } from '../../core/models/theme';

describe('ThemesList Component', () => {
  let component: ThemesList;
  let fixture: ComponentFixture<ThemesList>;
  let mockThemeService: any;

  const mockThemes: Theme[] = [
    { id: 1, titre: 'Thème 1', description: 'Description du thème 1', abonnement: false },
    { id: 2, titre: 'Thème 2', description: 'Description du thème 2', abonnement: true }
  ];

  beforeEach(async () => {
    mockThemeService = {
      all: jest.fn().mockReturnValue(of(mockThemes)),
      abonnement: jest.fn().mockReturnValue(of({ message: 'Abonnement réussi' }))
    };

    await TestBed.configureTestingModule({
      imports: [
        ThemesList,
        RouterTestingModule
      ],
      providers: [
        { provide: ThemeService, useValue: mockThemeService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ThemesList);
    component = fixture.componentInstance;
    
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
  
  it('should refresh themes list on abonnement', () => {
    const themeId = 1;
    component.abonnement(themeId);

    expect(mockThemeService.abonnement).toHaveBeenCalledWith(themeId);
    expect(mockThemeService.all).toHaveBeenCalledTimes(2);
  });
});