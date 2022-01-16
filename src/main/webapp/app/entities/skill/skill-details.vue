<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="skill">
        <h2 class="jh-entity-heading" data-cy="skillDetailsHeading">
          <span v-text="$t('mepsterApp.skill.detail.title')">Skill</span> {{ skill.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('mepsterApp.skill.title')">Title</span>
          </dt>
          <dd>
            <span>{{ skill.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.skill.description')">Description</span>
          </dt>
          <dd>
            <span>{{ skill.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.skill.projectPosition')">Project Position</span>
          </dt>
          <dd>
            <div v-if="skill.projectPosition">
              <router-link :to="{ name: 'ProjectPositionView', params: { projectPositionId: skill.projectPosition.id } }">{{
                skill.projectPosition.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('mepsterApp.skill.person')">Person</span>
          </dt>
          <dd>
            <div v-if="skill.person">
              <router-link :to="{ name: 'PersonView', params: { personId: skill.person.id } }">{{ skill.person.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="skill.id" :to="{ name: 'SkillEdit', params: { skillId: skill.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./skill-details.component.ts"></script>
