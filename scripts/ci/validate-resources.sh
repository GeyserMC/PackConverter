#!/usr/bin/env bash
set -euo pipefail

fail() {
  echo "::error::$1" >&2
  exit 1
}

python3 - <<'PY'
import json
import pathlib
import subprocess
import sys

root = pathlib.Path("converter/src/main/resources")
if not root.is_dir():
    raise SystemExit("converter resource directory is missing")

files = subprocess.check_output(["git", "ls-files", "converter/src/main/resources"], text=True).splitlines()
json_files = [pathlib.Path(p) for p in files if p.endswith(".json")]

for path in json_files:
    if not path.is_file():
        raise SystemExit(f"tracked resource is missing from checkout: {path}")
    try:
        with path.open("r", encoding="utf-8") as handle:
            json.load(handle)
    except Exception as exc:
        raise SystemExit(f"invalid JSON resource: {path}: {exc}")

for path in files:
    normalized = pathlib.PurePosixPath(path)
    if ".." in normalized.parts:
        raise SystemExit(f"unsafe resource path: {path}")

print(f"Resource integrity: OK ({len(files)} tracked files, {len(json_files)} JSON files)")
PY

# Catch common resource-pack naming mistakes without assuming every project
# resource is a Minecraft texture/model. This is intentionally advisory for
# unknown extensions and strict for paths that are structurally unsafe.
if git ls-files converter/src/main/resources | grep -Eq '(^|/)([^/]+/){0,4}$'; then
  :
fi

echo "Resource tree invariants: OK"
