<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="projectPosition">
        <h2 class="jh-entity-heading" data-cy="projectPositionDetailsHeading">
          <span v-text="$t('mepsterApp.projectPosition.detail.title')">ProjectPosition</span> {{ projectPosition.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mepsterApp.projectPosition.title')">Title</span>
          </dt>
          <dd>
            <span>{{ projectPosition.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.projectPosition.description')">Description</span>
          </dt>
          <dd>
            <span>{{ projectPosition.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.projectPosition.percent')">Percent</span>
          </dt>
          <dd>
            <span>{{ projectPosition.percent }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.projectPosition.role')">Role</span>
          </dt>
          <dd>
            <div v-if="projectPosition.role">
              <router-link :to="{ name: 'RoleView', params: { roleId: projectPosition.role.id } }">{{
                projectPosition.role.title
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.projectPosition.project')">Project</span>
          </dt>
          <dd>
            <div v-if="projectPosition.project">
              <router-link :to="{ name: 'ProjectView', params: { projectId: projectPosition.project.id } }">{{
                projectPosition.project.title
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="projectPosition.id"
          :to="{ name: 'ProjectPositionEdit', params: { projectPositionId: projectPosition.id } }"
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

<script lang="ts" src="./project-position-details.component.ts"></script>
