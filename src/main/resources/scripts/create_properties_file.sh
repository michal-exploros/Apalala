#! /bin/bash
#
#
echo buildResultKey=${bamboo.buildResultKey} > bamboo_variables.properties
echo branchName=${bamboo.planRepository.3.branch} >> bamboo_variables.properties
echo revision=${git rev-parse --short HEAD} >> bamboo_variables.properties