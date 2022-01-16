<template>
  <div>
    <h2 id="page-heading" data-cy="MonthlyAvailabilityHeading">
      <span v-text="$t('mepsterApp.monthlyAvailability.home.title')" id="monthly-availability-heading">Monthly Availabilities</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mepsterApp.monthlyAvailability.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MonthlyAvailabilityCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-monthly-availability"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mepsterApp.monthlyAvailability.home.createLabel')"> Create a new Monthly Availability </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && monthlyAvailabilities && monthlyAvailabilities.length === 0">
      <span v-text="$t('mepsterApp.monthlyAvailability.home.notFound')">No monthlyAvailabilities found</span>
    </div>
    <div class="table-responsive" v-if="monthlyAvailabilities && monthlyAvailabilities.length > 0">
      <table class="table table-striped" aria-describedby="monthlyAvailabilities">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyAvailability.yearmonth')">Yearmonth</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyAvailability.percent')">Percent</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyAvailability.isActive')">Is Active</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyAvailability.person')">Person</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="monthlyAvailability in monthlyAvailabilities" :key="monthlyAvailability.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MonthlyAvailabilityView', params: { monthlyAvailabilityId: monthlyAvailability.id } }">{{
                monthlyAvailability.id
              }}</router-link>
            </td>
            <td>{{ monthlyAvailability.yearmonth }}</td>
            <td>{{ monthlyAvailability.percent }}</td>
            <td>{{ monthlyAvailability.isActive }}</td>
            <td>
              <div v-if="monthlyAvailability.person">
                <router-link :to="{ name: 'PersonView', params: { personId: monthlyAvailability.person.id } }">{{
                  monthlyAvailability.person.lastname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MonthlyAvailabilityView', params: { monthlyAvailabilityId: monthlyAvailability.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'MonthlyAvailabilityEdit', params: { monthlyAvailabilityId: monthlyAvailability.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(monthlyAvailability)"
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
        ><span
          id="mepsterApp.monthlyAvailability.delete.question"
          data-cy="monthlyAvailabilityDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-monthlyAvailability-heading" v-text="$t('mepsterApp.monthlyAvailability.delete.question', { id: removeId })">
          Are you sure you want to delete this Monthly Availability?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-monthlyAvailability"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMonthlyAvailability()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./monthly-availability.component.ts"></script>
