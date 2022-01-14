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

describe('ProjectPosition e2e test', () => {
  const projectPositionPageUrl = '/project-position';
  const projectPositionPageUrlPattern = new RegExp('/project-position(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const projectPositionSample = { title: 'Via Argentina Garden' };

  let projectPosition: any;
  //let role: any;
  //let project: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/roles',
      body: {"title":"deposit"},
    }).then(({ body }) => {
      role = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/projects',
      body: {"title":"application Savings Benin","description":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","start":"2022-01-13","end":"2022-01-14","active":true,"notes":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ="},
    }).then(({ body }) => {
      project = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/project-positions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/project-positions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/project-positions/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/roles', {
      statusCode: 200,
      body: [role],
    });

    cy.intercept('GET', '/api/skills', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/projects', {
      statusCode: 200,
      body: [project],
    });

    cy.intercept('GET', '/api/monthly-project-position-assignments', {
      statusCode: 200,
      body: [],
    });

  });
   */

  afterEach(() => {
    if (projectPosition) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/project-positions/${projectPosition.id}`,
      }).then(() => {
        projectPosition = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (role) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/roles/${role.id}`,
      }).then(() => {
        role = undefined;
      });
    }
    if (project) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/projects/${project.id}`,
      }).then(() => {
        project = undefined;
      });
    }
  });
   */

  it('ProjectPositions menu should load ProjectPositions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('project-position');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ProjectPosition').should('exist');
    cy.url().should('match', projectPositionPageUrlPattern);
  });

  describe('ProjectPosition page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(projectPositionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ProjectPosition page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/project-position/new$'));
        cy.getEntityCreateUpdateHeading('ProjectPosition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', projectPositionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/project-positions',
          body: {
            ...projectPositionSample,
            role: role,
            project: project,
          },
        }).then(({ body }) => {
          projectPosition = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/project-positions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [projectPosition],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(projectPositionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(projectPositionPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response!.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details ProjectPosition page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('projectPosition');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', projectPositionPageUrlPattern);
      });

      it('edit button click should load edit ProjectPosition page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProjectPosition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', projectPositionPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of ProjectPosition', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('projectPosition').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', projectPositionPageUrlPattern);

        projectPosition = undefined;
      });
    });
  });

  describe('new ProjectPosition page', () => {
    beforeEach(() => {
      cy.visit(`${projectPositionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ProjectPosition');
    });

    it.skip('should create an instance of ProjectPosition', () => {
      cy.get(`[data-cy="title"]`).type('Orchestrator Seychel').should('have.value', 'Orchestrator Seychel');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="percent"]`).type('57').should('have.value', '57');

      cy.get(`[data-cy="role"]`).select(1);
      cy.get(`[data-cy="project"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        projectPosition = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', projectPositionPageUrlPattern);
    });
  });
});
