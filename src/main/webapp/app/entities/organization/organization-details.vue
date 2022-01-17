<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="organization">
        <h2 class="jh-entity-heading" data-cy="organizationDetailsHeading">
          <span v-text="$t('mepsterApp.organization.detail.title')">Organization</span> {{ organization.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mepsterApp.organization.name')">Name</span>
          </dt>
          <dd>
            <span>{{ organization.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.street')">Street</span>
          </dt>
          <dd>
            <span>{{ organization.street }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.number')">Number</span>
          </dt>
          <dd>
            <span>{{ organization.number }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.city')">City</span>
          </dt>
          <dd>
            <span>{{ organization.city }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.zipcode')">Zipcode</span>
          </dt>
          <dd>
            <span>{{ organization.zipcode }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.country')">Country</span>
          </dt>
          <dd>
            <span>{{ organization.country }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.phoneNumber')">Phone Number</span>
          </dt>
          <dd>
            <span>{{ organization.phoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.email')">Email</span>
          </dt>
          <dd>
            <span>{{ organization.email }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.website')">Website</span>
          </dt>
          <dd>
            <span>{{ organization.website }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ organization.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.organization.parentOrganization')">Parent Organization</span>
          </dt>
          <dd>
            <div v-if="organization.parentOrganization">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: organization.parentOrganization.id } }">{{
                organization.parentOrganization.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="organization.id"
          :to="{ name: 'OrganizationEdit', params: { organizationId: organization.id } }"
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

<script lang="ts" src="./organization-details.component.ts"></script>
