
service="ConnectedCar"
environment="Dev"

start=$(date -v-3d +"%s")

api=$(aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query "Stacks[0].Outputs[?OutputKey=='AdminAPI'].OutputValue" \
    --output text)

group="API-Gateway-Execution-Logs_${api}/api"
pattern="request body before transformations"

aws logs filter-log-events \
   --log-group-name $group \
   --start-time "${start}" \
   --filter-pattern "${pattern}" \
   --output text \
   --page-size 100
