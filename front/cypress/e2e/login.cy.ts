describe('Parcours de Connexion (Login)', () => {

  beforeEach(() => {
    cy.request('DELETE', 'http://localhost:9000/api/test/reset-database');
  });

  it('should log in user_1 and navigate to the Articles page', () => {
    cy.visit('/');
    cy.get('[data-testid="login"]').click();
    cy.url().should('include', '/login');

    cy.get('input[formControlName="identifiant"]').type('user_1', { force: true });
    cy.get('input[formControlName="password"]').type('user_1_PASSWORD', { force: true }); 
    cy.get('[data-testid="submit_button"]').click();

    cy.url().should('include', '/articles');
  });

  /* it('devrait afficher une erreur si les identifiants sont incorrects', () => {
    // On peut aller directement sur la page login pour ce test
    cy.visit('/login');

    // On entre des informations erronées
    cy.get('input[formControlName="identifiant"]').type('mauvais@email.com');
    cy.get('input[formControlName="password"]').type('MauvaisMotDePasse123!');

    // On soumet
    cy.get('app-bouton[type="submit"]').click();

    // Assertions : Vérifications d'échec
    // On doit rester sur la page de connexion
    cy.url().should('include', '/login');
    
    // Le message d'erreur doit s'afficher (grâce au data-testid défini dans login.html)
    cy.get('[data-testid="error-message"]')
      .should('be.visible')
      .and('not.be.empty');
  }); */

});