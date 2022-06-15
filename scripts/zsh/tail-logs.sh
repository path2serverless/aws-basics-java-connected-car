
service="ConnectedCar"
environment="Dev"

group="/aws/lambda/${service}_Admin_CreateDealer_${environment}"
pattern="exception"

aws logs tail $group \
    --format short \
    --follow \
    --filter-pattern "${pattern}"
