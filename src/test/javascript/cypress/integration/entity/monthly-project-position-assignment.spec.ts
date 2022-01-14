import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('MonthlyProjectPositionAssignment e2e test', () => {
  const monthlyProjectPositionAssignmentPageUrl = '/monthly-project-position-assignment';
  const monthlyProjectPositionAssignmentPageUrlPattern = new RegExp('/monthly-project-position-assignment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const monthlyProjectPositionAssignmentSample = { active: false };

  let monthlyProjectPositionAssignment: any;
  //let projectPosition: any;
  //let person: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/project-positions',
      body: {"title":"Versatile Ecuador sy","description":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","percent":41},
    }).then(({ body }) => {
      projectPosition = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/people',
      body: {"firstname":"Wells","lastname":"Barbados Divide","notes":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ="},
    }).then(({ body }) => {
      person = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/monthly-project-position-assignments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/monthly-project-position-assignments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/monthly-project-position-assignments/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/project-positions', {
      statusCode: 200,
      body: [projectPosition],
    });

    cy.intercept('GET', '/api/people', {
      statusCode: 200,
      body: [person],
    });

  });
   */

  afterEach(() => {
    if (monthlyProjectPositionAssignment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/monthly-project-position-assignments/${monthlyProjectPositionAssignment.id}`,
      }).then(() => {
        monthlyProjectPositionAssignment = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (projectPosition) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/project-positions/${projectPosition.id}`,
      }).then(() => {
        projectPosition = undefined;
      });
    }
    if (person) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/people/${person.id}`,
      }).then(() => {
        person = undefined;
      });
    }
  });
   */

  it('MonthlyProjectPositionAssignments menu should load MonthlyProjectPositionAssignments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('monthly-project-position-assignment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MonthlyProjectPositionAssignment').should('exist');
    cy.url().should('match', monthlyProjectPositionAssignmentPageUrlPattern);
  });

  describe('MonthlyProjectPositionAssignment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(monthlyProjectPositionAssignmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MonthlyProjectPositionAssignment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/monthly-project-position-assignment/new$'));
        cy.getEntityCreateUpdateHeading('MonthlyProjectPositionAssignment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyProjectPositionAssignmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/monthly-project-position-assignments',
          body: {
            ...monthlyProjectPositionAssignmentSample,
            projectPosition: projectPosition,
            person: person,
          },
        }).then(({ body }) => {
          monthlyProjectPositionAssignment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/monthly-project-position-assignments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [monthlyProjectPositionAssignment],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(monthlyProjectPositionAssignmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(monthlyProjectPositionAssignmentPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response!.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details MonthlyProjectPositionAssignment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('monthlyProjectPositionAssignment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyProjectPositionAssignmentPageUrlPattern);
      });

      it('edit button click should load edit MonthlyProjectPositionAssignment page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MonthlyProjectPositionAssignment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyProjectPositionAssignmentPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of MonthlyProjectPositionAssignment', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('monthlyProjectPositionAssignment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyProjectPositionAssignmentPageUrlPattern);

        monthlyProjectPositionAssignment = undefined;
      });
    });
  });

  describe('new MonthlyProjectPositionAssignment page', () => {
    beforeEach(() => {
      cy.visit(`${monthlyProjectPositionAssignmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MonthlyProjectPositionAssignment');
    });

    it.skip('should create an instance of MonthlyProjectPositionAssignment', () => {
      cy.get(`[data-cy="yearmonth"]`).type('2022-01-13').should('have.value', '2022-01-13');

      cy.get(`[data-cy="percent"]`).type('3').should('have.value', '3');

      cy.get(`[data-cy="active"]`).should('not.be.checked');
      cy.get(`[data-cy="active"]`).click().should('be.checked');

      cy.get(`[data-cy="projectPosition"]`).select(1);
      cy.get(`[data-cy="person"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        monthlyProjectPositionAssignment = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', monthlyProjectPositionAssignmentPageUrlPattern);
    });
  });
});
