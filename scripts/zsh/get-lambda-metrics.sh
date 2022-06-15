
service="ConnectedCar"
environment="Dev"

start=$(date -u -v-30M +"%Y-%m-%dT%H:%M:%S")
end=$(date -u +"%Y-%m-%dT%H:%M:%S")

dimensions="Name=FunctionName,Value=${service}_Admin_GetDealers_${environment}"

aws cloudwatch get-metric-statistics \
    --namespace AWS/Lambda \
    --metric-name Duration \
    --dimensions ${dimensions} \
    --start-time ${start} \
    --end-time ${end} \
    --period 60 \
    --statistics Average \
    --output json
