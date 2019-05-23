# hotel-search-demo
Demo API for HotelSearch by city


## Example cURL request:
```
curl -X GET \
  'http://localhost:8080/hotels/searchByCityId?cityId=Bangkok&priceSort=DESC' \
  -H 'apiKey: service-one' 
```
  

## Configuration file notes:
Configuration file is as per below format:
```
rate-limiter-config:
  defaultRateConfig:
    limitRequest: 1
    perSeconds: 10
    waitSeconds: 60
  api-configs:
    - apiKey: service-one
      apiRateConfig:
        limitRequest: 2
        perSeconds: 10
        waitSeconds: 60
```
- **limitRequest** = allowed number of requests
- **perSeconds** = #of limitRequests are allowed per how many seconds
- **waitSeconds** = suspend period in seconds (if ratelimit exceeds then wait for how many seconds)
- **api-configs** = set rate configurations based on different apiKey
- **defaultRateConfig** = default rate configuration (if any apiKey is not matched)
