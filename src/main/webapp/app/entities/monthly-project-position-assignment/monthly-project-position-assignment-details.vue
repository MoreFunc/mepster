<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="monthlyProjectPositionAssignment">
        <h2 class="jh-entity-heading" data-cy="monthlyProjectPositionAssignmentDetailsHeading">
          <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.detail.title')">MonthlyProjectPositionAssignment</span>
          {{ monthlyProjectPositionAssignment.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.yearmonth')">Yearmonth</span>
          </dt>
          <dd>
            <span>{{ monthlyProjectPositionAssignment.yearmonth }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.percent')">Percent</span>
          </dt>
          <dd>
            <span>{{ monthlyProjectPositionAssignment.percent }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.active')">Active</span>
          </dt>
          <dd>
            <span>{{ monthlyProjectPositionAssignment.active }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.projectPosition')">Project Position</span>
          </dt>
          <dd>
            <div v-if="monthlyProjectPositionAssignment.projectPosition">
              <router-link
                :to="{ name: 'ProjectPositionView', params: { projectPositionId: monthlyProjectPositionAssignment.projectPosition.id } }"
                >{{ monthlyProjectPositionAssignment.projectPosition.title }}</router-link
              >
            </div>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.monthlyProjectPositionAssignment.person')">Person</span>
          </dt>
          <dd>
            <div v-if="monthlyProjectPositionAssignment.person">
              <router-link :to="{ name: 'PersonView', params: { personId: monthlyProjectPositionAssignment.person.id } }">{{
                monthlyProjectPositionAssignment.person.lastname
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="monthlyProjectPositionAssignment.id"
          :to="{
            name: 'MonthlyProjectPositionAssignmentEdit',
            params: { monthlyProjectPositionAssignmentId: monthlyProjectPositionAssignment.id },
          }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./monthly-project-position-assignment-details.component.ts"></script>
