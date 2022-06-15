
service="ConnectedCar"
environment="Dev"

start=$(date -u -v-30M +"%Y-%m-%dT%H:%M:%S")
end=$(date -u +"%Y-%m-%dT%H:%M:%S")

dimensions="Name=ApiName,Value=${service}_Admin_API_${environment} Name=Stage,Value=api"

aws cloudwatch get-metric-statistics \
    --namespace AWS/ApiGateway \
    --metric-name Latency \
    --dimensions ${dimensions} \
    --start-time ${start} \
    --end-time ${end} \
    --period 60 \
    --statistics Average \
    --output text
