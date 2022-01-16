<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="project">
        <h2 class="jh-entity-heading" data-cy="projectDetailsHeading">
          <span v-text="$t('mepsterApp.project.detail.title')">Project</span> {{ project.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mepsterApp.project.title')">Title</span>
          </dt>
          <dd>
            <span>{{ project.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.project.description')">Description</span>
          </dt>
          <dd>
            <span>{{ project.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.project.startDate')">Start Date</span>
          </dt>
          <dd>
            <span>{{ project.startDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.project.endDate')">End Date</span>
          </dt>
          <dd>
            <span>{{ project.endDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.project.isActive')">Is Active</span>
          </dt>
          <dd>
            <span>{{ project.isActive }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.project.chancePercent')">Chance Percent</span>
          </dt>
          <dd>
            <span>{{ project.chancePercent }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.project.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ project.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.project.organization')">Organization</span>
          </dt>
          <dd>
            <div v-if="project.organization">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: project.organization.id } }">{{
                project.organization.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="project.id" :to="{ name: 'ProjectEdit', params: { projectId: project.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./project-details.component.ts"></script>
