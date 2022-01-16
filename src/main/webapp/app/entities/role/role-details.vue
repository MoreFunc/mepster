<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="role">
        <h2 class="jh-entity-heading" data-cy="roleDetailsHeading">
          <span v-text="$t('mepsterApp.role.detail.title')">Role</span> {{ role.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mepsterApp.role.title')">Title</span>
          </dt>
          <dd>
            <span>{{ role.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.role.person')">Person</span>
          </dt>
          <dd>
            <div v-if="role.person">
              <router-link :to="{ name: 'PersonView', params: { personId: role.person.id } }">{{ role.person.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="role.id" :to="{ name: 'RoleEdit', params: { roleId: role.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./role-details.component.ts"></script>
