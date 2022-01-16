<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="monthlyAvailability">
        <h2 class="jh-entity-heading" data-cy="monthlyAvailabilityDetailsHeading">
          <span v-text="$t('mepsterApp.monthlyAvailability.detail.title')">MonthlyAvailability</span> {{ monthlyAvailability.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mepsterApp.monthlyAvailability.yearmonth')">Yearmonth</span>
          </dt>
          <dd>
            <span>{{ monthlyAvailability.yearmonth }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.monthlyAvailability.percent')">Percent</span>
          </dt>
          <dd>
            <span>{{ monthlyAvailability.percent }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.monthlyAvailability.isActive')">Is Active</span>
          </dt>
          <dd>
            <span>{{ monthlyAvailability.isActive }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.monthlyAvailability.person')">Person</span>
          </dt>
          <dd>
            <div v-if="monthlyAvailability.person">
              <router-link :to="{ name: 'PersonView', params: { personId: monthlyAvailability.person.id } }">{{
                monthlyAvailability.person.lastname
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="monthlyAvailability.id"
          :to="{ name: 'MonthlyAvailabilityEdit', params: { monthlyAvailabilityId: monthlyAvailability.id } }"
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

<script lang="ts" src="./monthly-availability-details.component.ts"></script>
