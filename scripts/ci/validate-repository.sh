#!/usr/bin/env bash
set -euo pipefail

fail() {
  echo "::error::$1" >&2
  exit 1
}

[[ -f gradlew ]] || fail "Gradle wrapper is missing"
[[ -x gradlew ]] || fail "Gradle wrapper is not executable"
[[ -f gradle.properties ]] || fail "gradle.properties is missing"
grep -Eq '^version=[^[:space:]]+$' gradle.properties || fail "gradle.properties has no valid version"

for required in \
  settings.gradle.kts \
  build.gradle.kts \
  converter/build.gradle.kts \
  bootstrap/build.gradle.kts; do
  [[ -f "$required" ]] || fail "Required build file is missing: $required"
done

# The fork must never accidentally execute upstream publishing actions.
if [[ -f .github/workflows/build.yml ]]; then
  if grep -Eq 'github\.repository *== *['"'"']GeyserMC/PackConverter['"'"']' .github/workflows/build.yml; then
    echo "Upstream publish guards detected."
  else
    fail "build.yml is missing the upstream repository publish guard"
  fi
fi

# Reject accidentally committed private-key material in workflow definitions.
if grep -RInE 'BEGIN (RSA|OPENSSH|EC|DSA) PRIVATE KEY' .github --exclude-dir='ISSUE_TEMPLATE' >/tmp/credential-scan.txt 2>/dev/null; then
  cat /tmp/credential-scan.txt
  fail "Potential private-key material found under .github"
fi

# Ensure generated build output is not tracked by the repository. The Gradle
# wrapper JAR is intentionally versioned and is the one supported exception.
tracked_generated=$(git ls-files | grep -E '(^|/)(build/|\.gradle/)|\.class$|\.jar$' | grep -Ev '^gradle/wrapper/gradle-wrapper\.jar$' || true)
if [[ -n "$tracked_generated" ]]; then
  printf '%s\n' "$tracked_generated"
  fail "Generated build output is tracked"
fi

echo "Repository invariants: OK"
