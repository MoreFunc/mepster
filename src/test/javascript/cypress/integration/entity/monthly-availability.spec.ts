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

describe('MonthlyAvailability e2e test', () => {
  const monthlyAvailabilityPageUrl = '/monthly-availability';
  const monthlyAvailabilityPageUrlPattern = new RegExp('/monthly-availability(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const monthlyAvailabilitySample = { isActive: true };

  let monthlyAvailability: any;
  let person: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/people',
      body: {
        firstname: 'Rubber Direct',
        lastname: 'generate haptic bypass',
        type: 'Inlet JBOD',
        lead: 'PNG Indiana',
        phoneNumber: '7',
        email: '1a@fVYT.othNH.QPT.eKMu',
        notes: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
      },
    }).then(({ body }) => {
      person = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/monthly-availabilities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/monthly-availabilities').as('postEntityRequest');
    cy.intercept('DELETE', '/api/monthly-availabilities/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/people', {
      statusCode: 200,
      body: [person],
    });
  });

  afterEach(() => {
    if (monthlyAvailability) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/monthly-availabilities/${monthlyAvailability.id}`,
      }).then(() => {
        monthlyAvailability = undefined;
      });
    }
  });

  afterEach(() => {
    if (person) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/people/${person.id}`,
      }).then(() => {
        person = undefined;
      });
    }
  });

  it('MonthlyAvailabilities menu should load MonthlyAvailabilities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('monthly-availability');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MonthlyAvailability').should('exist');
    cy.url().should('match', monthlyAvailabilityPageUrlPattern);
  });

  describe('MonthlyAvailability page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(monthlyAvailabilityPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MonthlyAvailability page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/monthly-availability/new$'));
        cy.getEntityCreateUpdateHeading('MonthlyAvailability');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyAvailabilityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/monthly-availabilities',
          body: {
            ...monthlyAvailabilitySample,
            person: person,
          },
        }).then(({ body }) => {
          monthlyAvailability = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/monthly-availabilities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [monthlyAvailability],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(monthlyAvailabilityPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MonthlyAvailability page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('monthlyAvailability');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyAvailabilityPageUrlPattern);
      });

      it('edit button click should load edit MonthlyAvailability page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MonthlyAvailability');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyAvailabilityPageUrlPattern);
      });

      it('last delete button click should delete instance of MonthlyAvailability', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('monthlyAvailability').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyAvailabilityPageUrlPattern);

        monthlyAvailability = undefined;
      });
    });
  });

  describe('new MonthlyAvailability page', () => {
    beforeEach(() => {
      cy.visit(`${monthlyAvailabilityPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MonthlyAvailability');
    });

    it('should create an instance of MonthlyAvailability', () => {
      cy.get(`[data-cy="yearmonth"]`).type('2022-01-16').should('have.value', '2022-01-16');

      cy.get(`[data-cy="percent"]`).type('27').should('have.value', '27');

      cy.get(`[data-cy="isActive"]`).should('not.be.checked');
      cy.get(`[data-cy="isActive"]`).click().should('be.checked');

      cy.get(`[data-cy="person"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        monthlyAvailability = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', monthlyAvailabilityPageUrlPattern);
    });
  });
});
