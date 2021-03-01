package ru.job4jweather_reactive.service;

import ru.job4jweather_reactive.model.Weather;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Andrey
 * @version 1
 * @date 3/1/2021
 */

@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();
    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Minsk", 15));
        weathers.put(7, new Weather(7, "Crimea", 35));
        weathers.put(8, new Weather(8, "Baghdad", 37));
        weathers.put(9, new Weather(9, "Rabat", 32));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> findHottestCity() {
        return Mono.justOrEmpty(weathers.values().stream().max(Comparator.comparing(Weather::getTemperature)));
    }

    public Flux<Weather> getCityTemperatureGreatThen(int t) {
        Map<Integer, Weather> result = weathers.entrySet()
                .stream()
                .filter(map -> map.getValue().getTemperature() > t)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return Flux.fromIterable(result.values());

    }
}
