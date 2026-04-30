import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NavBar } from './nav-bar';
import { SessionService } from '../../../core/services/session';

describe('NavBar Component', () => {
  let component: NavBar;
  let fixture: ComponentFixture<NavBar>;
  let mockSessionService: any;

  beforeEach(async () => {
    mockSessionService = {
      logOut: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [NavBar, RouterTestingModule],
      providers: [
        { provide: SessionService, useValue: mockSessionService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(NavBar);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should logout', () => {
    component.logout();
    expect(mockSessionService.logOut).toHaveBeenCalled();
  });
});