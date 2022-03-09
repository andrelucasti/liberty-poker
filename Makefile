DEFAULT_GOAL := help

.PHONY: help
help: ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

infra-local-start: ## Run infrastructure locally
	docker-compose -f infrastructure/docker-compose.yml up -d --build

infra-local-stop: ## Stop infrastructure locally
	docker-compose -f localstack/docker-compose.yml down

infra-local-db-cleanup: ## Clean the database locally
	docker stop postgres-planningpoker \
    & docker rm postgres-planningpoker \
    & docker volume rm postgres_postgres-planningpoker

run-test: ## run all test, unit | integration | e2e
	mvn clean test

run-build:
	mvn clean install

run-app-with-h2: ## run the app with h2 (data-base-in-memory)
	mvn clean install && mvn -f app/pom.xml spring-boot:run -Dspring-boot.run.profiles=h2

run-app-with-postgres: ## run the app with postgres database
	docker-compose -f infrastructure/docker-compose.yml up -d --build && \
	mvn clean install && mvn -f app/pom.xml spring-boot:run -Dspring-boot.run.profiles=postgres
