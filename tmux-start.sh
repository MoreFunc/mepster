#!/bin/bash

session="mepster-dev"

tmux new-session -d -s ${session}

window=0
tmux rename-window -t ${session}:${window} 'jhipster-registry'
tmux send-keys -t ${session}:${window} 'docker-compose -f src/main/docker/jhipster-registry.yml up'

window=1
tmux new-window -t ${session}:${window} -n 'jhipster-control-center'
tmux send-keys -t ${session}:${window} 'docker-compose -f src/main/docker/jhipster-control-center.yml up'

window=2
tmux new-window -t ${session}:${window} -n 'mepster-postgresql'
tmux send-keys -t ${session}:${window} 'docker-compose -f src/main/docker/postgresql.yml up'

window=3
tmux new-window -t ${session}:${window} -n 'mepster-ui'
tmux send-keys -t ${session}:${window} 'npm start'

tmux attach-session -t ${session}

