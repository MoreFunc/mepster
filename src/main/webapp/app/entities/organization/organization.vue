<template>
  <div>
    <h2 id="page-heading" data-cy="OrganizationHeading">
      <span v-text="$t('mepsterApp.organization.home.title')" id="organization-heading">Organizations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mepsterApp.organization.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'OrganizationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-organization"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mepsterApp.organization.home.createLabel')"> Create a new Organization </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && organizations && organizations.length === 0">
      <span v-text="$t('mepsterApp.organization.home.notFound')">No organizations found</span>
    </div>
    <div class="table-responsive" v-if="organizations && organizations.length > 0">
      <table class="table table-striped" aria-describedby="organizations">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.name')">Name</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.street')">Street</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.number')">Number</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.city')">City</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.zipcode')">Zipcode</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.country')">Country</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.phoneNumber')">Phone Number</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.email')">Email</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.website')">Website</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.notes')">Notes</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.organization.parentOrganization')">Parent Organization</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="organization in organizations" :key="organization.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: organization.id } }">{{
                organization.id
              }}</router-link>
            </td>
            <td>{{ organization.name }}</td>
            <td>{{ organization.street }}</td>
            <td>{{ organization.number }}</td>
            <td>{{ organization.city }}</td>
            <td>{{ organization.zipcode }}</td>
            <td>{{ organization.country }}</td>
            <td>{{ organization.phoneNumber }}</td>
            <td>{{ organization.email }}</td>
            <td>{{ organization.website }}</td>
            <td>{{ organization.notes }}</td>
            <td>
              <div v-if="organization.parentOrganization">
                <router-link :to="{ name: 'OrganizationView', params: { organizationId: organization.parentOrganization.id } }">{{
                  organization.parentOrganization.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OrganizationView', params: { organizationId: organization.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OrganizationEdit', params: { organizationId: organization.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(organization)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="mepsterApp.organization.delete.question" data-cy="organizationDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-organization-heading" v-text="$t('mepsterApp.organization.delete.question', { id: removeId })">
          Are you sure you want to delete this Organization?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-organization"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeOrganization()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./organization.component.ts"></script>
