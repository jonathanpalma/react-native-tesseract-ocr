# Development Setup Guide

This document describes how to set up the development environment for react-native-tesseract-ocr.

## Phase 1 Modernization Complete ✅

The following modernizations have been completed:

### Dependencies Updated
- React Native: `0.61.5` → `0.76.0+`
- React: `^16.9.0` → `^18.2.0`
- Android SDK: `28` → `34`
- Gradle: `3.4.1` → `8.2.0`
- Added TypeScript support
- Added modern development tools (ESLint, Prettier, Jest)

### Build System Modernized
- Updated Android build configuration
- Removed deprecated jcenter repository
- Added namespace to Android library
- Updated iOS deployment target to 12.0+
- Added Java 11 compatibility

### TypeScript Support Added
- Complete TypeScript rewrite of JavaScript code
- Type definitions for all APIs
- Modern build system with TypeScript compilation
- Backwards compatibility maintained via index.js wrapper

## Setup Instructions

### Prerequisites
- Node.js 18+
- React Native development environment
- Android Studio with SDK 34
- Xcode 15+ (for iOS development)

### Installation for Development

1. **Install dependencies:**
   ```bash
   npm install
   ```

2. **Build TypeScript:**
   ```bash
   npm run build
   ```

3. **Run type checking:**
   ```bash
   npm run typecheck
   ```

4. **Run linting:**
   ```bash
   npm run lint
   ```

5. **Format code:**
   ```bash
   npm run format
   ```

### For End Users

To use this modernized library in your React Native project:

```bash
npm install react-native-tesseract-ocr@latest
```

**Minimum Requirements:**
- React Native 0.74.0+
- React 18.0.0+
- iOS 12.0+
- Android API 21+

## Breaking Changes from 2.x

- Minimum React Native version: 0.74+
- Minimum iOS version: 12.0+
- Minimum Android API: 21+
- Node.js 18+ required for development

## What's Next

Phase 2 will focus on:
- Complete iOS implementation (currently only stub)
- New Architecture support (TurboModules & Fabric)
- Performance optimizations
- Bug fixes for data path and custom language issues

## Testing the Build

After running `npm install` in a project with React Native 0.76+:

```typescript
import TesseractOcr, { LANG_ENGLISH } from 'react-native-tesseract-ocr';

// Basic usage (Android only currently)
const text = await TesseractOcr.recognize('/path/to/image.jpg', LANG_ENGLISH);
console.log('Recognized text:', text);
```

Note: iOS implementation is still a placeholder and will be completed in Phase 2.