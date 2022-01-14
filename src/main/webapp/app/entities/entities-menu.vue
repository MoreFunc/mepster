<template>
  <div>
    <b-dropdown-item to="/organization">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.organization')">Organization</span>
    </b-dropdown-item>
    <b-dropdown-item to="/person">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.person')">Person</span>
    </b-dropdown-item>
    <b-dropdown-item to="/role">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.role')">Role</span>
    </b-dropdown-item>
    <b-dropdown-item to="/project">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.project')">Project</span>
    </b-dropdown-item>
    <b-dropdown-item to="/project-position">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.projectPosition')">Project Position</span>
    </b-dropdown-item>
    <b-dropdown-item to="/skill">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.skill')">Skill</span>
    </b-dropdown-item>
    <b-dropdown-item to="/monthly-project-position-assignment">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.monthlyProjectPositionAssignment')">Monthly Project Position Assignment</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
