<template>
  <div>
    <h2 id="page-heading" data-cy="MonthlyProjectPositionAssignmentHeading">
      <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.home.title')" id="monthly-project-position-assignment-heading"
        >Monthly Project Position Assignments</span
      >
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MonthlyProjectPositionAssignmentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-monthly-project-position-assignment"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.home.createLabel')">
              Create a new Monthly Project Position Assignment
            </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div
      class="alert alert-warning"
      v-if="!isFetching && monthlyProjectPositionAssignments && monthlyProjectPositionAssignments.length === 0"
    >
      <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.home.notFound')">No monthlyProjectPositionAssignments found</span>
    </div>
    <div class="table-responsive" v-if="monthlyProjectPositionAssignments && monthlyProjectPositionAssignments.length > 0">
      <table class="table table-striped" aria-describedby="monthlyProjectPositionAssignments">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.yearmonth')">Yearmonth</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.percent')">Percent</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.active')">Active</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.projectPosition')">Project Position</span></th>
            <th scope="row"><span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.person')">Person</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="monthlyProjectPositionAssignment in monthlyProjectPositionAssignments"
            :key="monthlyProjectPositionAssignment.id"
            data-cy="entityTable"
          >
            <td>
              <router-link
                :to="{
                  name: 'MonthlyProjectPositionAssignmentView',
                  params: { monthlyProjectPositionAssignmentId: monthlyProjectPositionAssignment.id },
                }"
                >{{ monthlyProjectPositionAssignment.id }}</router-link
              >
            </td>
            <td>{{ monthlyProjectPositionAssignment.yearmonth }}</td>
            <td>{{ monthlyProjectPositionAssignment.percent }}</td>
            <td>{{ monthlyProjectPositionAssignment.active }}</td>
            <td>
              <div v-if="monthlyProjectPositionAssignment.projectPosition">
                <router-link
                  :to="{ name: 'ProjectPositionView', params: { projectPositionId: monthlyProjectPositionAssignment.projectPosition.id } }"
                  >{{ monthlyProjectPositionAssignment.projectPosition.title }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="monthlyProjectPositionAssignment.person">
                <router-link :to="{ name: 'PersonView', params: { personId: monthlyProjectPositionAssignment.person.id } }">{{
                  monthlyProjectPositionAssignment.person.lastname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{
                    name: 'MonthlyProjectPositionAssignmentView',
                    params: { monthlyProjectPositionAssignmentId: monthlyProjectPositionAssignment.id },
                  }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{
                    name: 'MonthlyProjectPositionAssignmentEdit',
                    params: { monthlyProjectPositionAssignmentId: monthlyProjectPositionAssignment.id },
                  }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(monthlyProjectPositionAssignment)"
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
          id="mepsterApp.monthlyProjectPositionAssignment.delete.question"
          data-cy="monthlyProjectPositionAssignmentDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-monthlyProjectPositionAssignment-heading"
          v-text="$t('mepsterApp.monthlyProjectPositionAssignment.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Monthly Project Position Assignment?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-monthlyProjectPositionAssignment"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMonthlyProjectPositionAssignment()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./monthly-project-position-assignment.component.ts"></script>
